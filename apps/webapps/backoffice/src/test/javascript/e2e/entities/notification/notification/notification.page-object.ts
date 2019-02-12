import { element, by, ElementFinder } from 'protractor';

export class NotificationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('app-notification div table .btn-danger'));
    title = element.all(by.css('app-notification div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class NotificationUpdatePage {
    pageTitle = element(by.id('app-notification-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    detailsInput = element(by.id('field_details'));
    sentDateInput = element(by.id('field_sentDate'));
    formatSelect = element(by.id('field_format'));
    userIdInput = element(by.id('field_userId'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setDetailsInput(details) {
        await this.detailsInput.sendKeys(details);
    }

    async getDetailsInput() {
        return this.detailsInput.getAttribute('value');
    }

    async setSentDateInput(sentDate) {
        await this.sentDateInput.sendKeys(sentDate);
    }

    async getSentDateInput() {
        return this.sentDateInput.getAttribute('value');
    }

    async setFormatSelect(format) {
        await this.formatSelect.sendKeys(format);
    }

    async getFormatSelect() {
        return this.formatSelect.element(by.css('option:checked')).getText();
    }

    async formatSelectLastOption() {
        await this.formatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setUserIdInput(userId) {
        await this.userIdInput.sendKeys(userId);
    }

    async getUserIdInput() {
        return this.userIdInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class NotificationDeleteDialog {
    private dialogTitle = element(by.id('app-delete-notification-heading'));
    private confirmButton = element(by.id('app-confirm-delete-notification'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
