import { element, by, ElementFinder } from 'protractor';

export class ProblemUpdatesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-problem-updates div table .btn-danger'));
    title = element.all(by.css('jhi-problem-updates div h2#page-heading span')).first();

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

export class ProblemUpdatesUpdatePage {
    pageTitle = element(by.id('jhi-problem-updates-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    updatedAtInput = element(by.id('field_updatedAt'));
    updateTextInput = element(by.id('field_updateText'));
    probUpdateSelect = element(by.id('field_probUpdate'));

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

    async probUpdateSelectLastOption() {
        await this.probUpdateSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async probUpdateSelectOption(option) {
        await this.probUpdateSelect.sendKeys(option);
    }

    getProbUpdateSelect(): ElementFinder {
        return this.probUpdateSelect;
    }

    async getProbUpdateSelectedOption() {
        return this.probUpdateSelect.element(by.css('option:checked')).getText();
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

export class ProblemUpdatesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-problemUpdates-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-problemUpdates'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
