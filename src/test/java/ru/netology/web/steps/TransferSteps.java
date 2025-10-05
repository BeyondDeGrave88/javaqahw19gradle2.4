package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashBoardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {
    private LoginPage loginPage;
    private DashBoardPage dashboardPage;
    private VerificationPage verificationPage;
    private TransferPage transferPage;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void userIsLoggedIn(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);

        DataHelper.AuthInfo authInfo = new DataHelper.AuthInfo(login, password);
        verificationPage = loginPage.validLogin(authInfo);

        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.verifyPageIsVisible();
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void userTransfersMoney(String amount, String fromCardNumber, String toCardNumber) {
        int transferAmount = Integer.parseInt(amount);
        int cardNumber = Integer.parseInt(toCardNumber);

        DataHelper.CardInfo toCard;
        if (cardNumber == 1) {
            toCard = DataHelper.getFirstCardInfo();
        } else {
            toCard = DataHelper.getSecondCardInfo();
        }

        transferPage = dashboardPage.selectCardToTransfer(toCard.getTestId());

        dashboardPage = transferPage.makeTransfer(fromCardNumber, transferAmount);
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void verifyCardBalance(String cardNumber, String expectedBalance) {
        int cardNum = Integer.parseInt(cardNumber);
        int expectedBal = Integer.parseInt(expectedBalance);

        DataHelper.CardInfo card;
        if (cardNum == 1) {
            card = DataHelper.getFirstCardInfo();
        } else {
            card = DataHelper.getSecondCardInfo();
        }

        int actualBalance = dashboardPage.getCardBalance(card.getTestId());

        assertEquals(expectedBal, actualBalance,
                "Баланс карты " + cardNum + " должен быть " + expectedBal + " рублей, но был " + actualBalance);
    }
}