package helpers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import utils.ConfigReader;

public class ADOHelper {

    private static final String PAT_TOKEN = ConfigReader.get("adoPatToken");
    private static final String ORG_URL = ConfigReader.get("adoOrgUrl");
    private static final String PROJECT_NAME = ConfigReader.get("adoProjectName");

    private static final HttpClient client = HttpClient.newHttpClient();

    // Step 1: Create Test Result
    public static String createTestResult(String testRunId, String testCaseTitle) {
        try {
            String url = ORG_URL + "/" + PROJECT_NAME + "/_apis/test/runs/" + testRunId + "/results?api-version=5.0";
            String jsonBody = "[{\"testCaseTitle\": \"" + testCaseTitle + "\", \"automatedTestName\": \""
                    + testCaseTitle + "\", \"outcome\": \"NotExecuted\"}]";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + encodePAT(PAT_TOKEN))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Test Result Created Successfully: " + response.body());
                return parseTestResultId(response.body());
            } else {
                System.out.println("Failed to create test result. Response: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Step 2: Update Test Result
    public static void updateTestResult(String testRunId, String testResultId, String status) {
        try {
            String url = ORG_URL + "/" + PROJECT_NAME + "/_apis/test/runs/" + testRunId + "/results?api-version=5.0";
            String jsonBody = "[{\"id\": " + testResultId + ", \"outcome\": \"" + status + "\"}]";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + encodePAT(PAT_TOKEN))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Test Result Updated Successfully: " + status);
            } else {
                System.out.println("Failed to update test result. Response: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Step 3: Update Test Case State
    public static void updateTestCaseState(String testCaseId, String newState) {
        try {
            String url = ORG_URL + "/_apis/wit/workitems/" + testCaseId + "?api-version=5.0";

            // JSON body to update the state field
            String jsonBody = "[{\"op\": \"add\", \"path\": \"/fields/System.State\", \"value\": \"" + newState + "\"}]";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json-patch+json")
                    .header("Authorization", "Basic " + encodePAT(PAT_TOKEN))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("Test Case State Updated Successfully: " + newState);
            } else {
                System.out.println("Failed to update Test Case State. Response: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encodePAT(String pat) {
        return Base64.getEncoder().encodeToString((":" + pat).getBytes(StandardCharsets.UTF_8));
    }

    private static String parseTestResultId(String responseBody) {
        return responseBody.split("\"id\":")[1].split(",")[0].trim();
    }
}
