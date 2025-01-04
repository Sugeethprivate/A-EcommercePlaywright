package enums;

public enum TestGroup {
    SMOKE("smoke"),
    REGRESSION("regression"),
    SANITY("sanity"),
    PERFORMANCE("performance");

    private final String groupName;

    TestGroup(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public static TestGroup fromString(String groupName) {
        for (TestGroup group : TestGroup.values()) {
            if (group.getGroupName().equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Unknown group: " + groupName);
    }

    @Override
    public String toString() {
        return groupName;
    }
}
