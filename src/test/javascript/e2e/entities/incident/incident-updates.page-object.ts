import { element, by, ElementFinder } from 'protractor';

export class IncidentUpdatesComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-incident-updates div table .btn-danger'));
    title = element.all(by.css('jhi-incident-updates div h2#page-heading span')).first();
    incidentDeleteButton = element(by.id('incident-delete'));

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnIncidentDeleteButton() {
        this.incidentDeleteButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async clickOnFirstDeleteButton() {
        await this.deleteButtons.first().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class IncidentUpdatesUpdatePage {
    pageTitle = element(by.id('jhi-incident-updates-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    updatedAtInput = element(by.id('field_updatedAt'));
    updateTextInput = element(by.id('field_updateText'));
    inUpdateSelect = element(by.id('field_inUpdate'));

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

    async inUpdateSelectLastOption() {
        await this.inUpdateSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async inUpdateSelectOption(option) {
        await this.inUpdateSelect.sendKeys(option);
    }

    getInUpdateSelect(): ElementFinder {
        return this.inUpdateSelect;
    }

    async getInUpdateSelectedOption() {
        return this.inUpdateSelect.element(by.css('option:checked')).getText();
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

export class IncidentUpdatesDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-incidentUpdates-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-incidentUpdates'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}

export class IncidentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-incident-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-incident'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
