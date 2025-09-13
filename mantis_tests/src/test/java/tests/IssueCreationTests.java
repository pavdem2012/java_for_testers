package tests;

import common.CommonFunctions;
import manager.model.IssueData;
import org.junit.jupiter.api.Test;

public class IssueCreationTests extends TestBase{
    @Test
    void canCreateIssue() {
        app.rest().createIssue(new IssueData()
                .withSummary(CommonFunctions.randomString(10))
                .withDescription(CommonFunctions.randomString(10))
                .withProject(1L));

    }
}
