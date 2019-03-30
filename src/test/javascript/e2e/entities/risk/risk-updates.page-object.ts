import { element, by, ElementFinder } from 'protractor';

export class RiskUpdatesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-risk-updates div table .btn-danger'));
    title = element.all(by.css('jhi-risk-updates div h2#page-heading span')).first();

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
        return this.title.getText();
    }
}

export class RiskUpdatesUpdatePage {
    pageTitle = element(by.id('jhi-risk-updates-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    updatedAtInput = element(by.id('field_updatedAt'));
    updateTextInput = element(by.id('field_updateText'));
    riskkUpdateSelect = element(by.id('field_riskkUpdate'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setUpdatedAtInput(updatedAt) {
        await this.updatedAtInput.sendKeys(updatedAt);
    }

    async getUpdatedAtInput() {
        return this.updatedAtInput.getAttribute('value');
    }

    async setUpdateTextInput(updateText) {
        await this.updateTextInput.sendKeys(updateText);
    }

    async getUpdateTextInput() {
        return this.updateTextInput.getAttribute('value');
    }

    async riskkUpdateSelectLastOption() {
        await this.riskkUpdateSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async riskkUpdateSelectOption(option) {
        await this.riskkUpdateSelect.sendKeys(option);
    }

    getRiskkUpdateSelect(): ElementFinder {
        return this.riskkUpdateSelect;
    }

    async getRiskkUpdateSelectedOption() {
        return this.riskkUpdateSelect.element(by.css('option:checked')).getText();
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

export class RiskUpdatesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-riskUpdates-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-riskUpdates'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
