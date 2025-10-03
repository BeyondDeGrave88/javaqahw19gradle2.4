package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashBoardPage {
    private final SelenideElement header = $("[data-test-id=dashboard]");

    public DashBoardPage() {
        header.should(Condition.visible);
    }
}
