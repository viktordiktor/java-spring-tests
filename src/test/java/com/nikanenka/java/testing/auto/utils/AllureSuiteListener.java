package com.nikanenka.java.testing.auto.utils;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.TestResult;

import java.util.List;
import java.util.Set;

/**
 * Replaces the default surefire/testng suite path with a short one: API/UI -> test class.
 * <p>
 * Runs right before a result is written to disk, after Allure has merged the default
 * suite labels, so nothing can overwrite the values.
 * Registered through {@code META-INF/services/io.qameta.allure.listener.TestLifecycleListener}.
 */
public class AllureSuiteListener implements TestLifecycleListener {

    private static final Set<String> SUITE_LABELS = Set.of("parentSuite", "suite", "subSuite");
    private static final String PACKAGE_LABEL = "package";
    private static final String TEST_CLASS_LABEL = "testClass";

    @Override
    public void beforeTestWrite(TestResult result) {
        String packageName = labelValue(result.getLabels(), PACKAGE_LABEL);
        String testClass = labelValue(result.getLabels(), TEST_CLASS_LABEL);
        if (packageName == null || testClass == null) {
            return;
        }

        result.getLabels().removeIf(label -> SUITE_LABELS.contains(label.getName()));
        result.getLabels().add(new Label().setName("suite").setValue(suiteName(packageName)));
        result.getLabels().add(new Label().setName("subSuite").setValue(simpleName(testClass)));
    }

    private static String suiteName(String packageName) {
        if (packageName.contains(".api.tests.")) {
            return "API";
        }
        if (packageName.contains(".ui.tests.")) {
            return "UI";
        }
        return "TESTS";
    }

    private static String simpleName(String testClass) {
        return testClass.substring(testClass.lastIndexOf('.') + 1);
    }

    private static String labelValue(List<Label> labels, String name) {
        if (labels == null) {
            return null;
        }
        return labels.stream()
                .filter(label -> name.equals(label.getName()))
                .map(Label::getValue)
                .findFirst()
                .orElse(null);
    }
}
