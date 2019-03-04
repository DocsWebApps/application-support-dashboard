import { element, by, ElementFinder } from 'protractor';

export class AppComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-app div table .btn-danger'));
    title = element.all(by.css('jhi-app div h2#page-heading span')).first();

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

export class AppUpdatePage {
    pageTitle = element(by.id('jhi-app-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    problemCountInput = element(by.id('field_problemCount'));
    sysStatusSelect = element(by.id('field_sysStatus'));
    lastProblemDateInput = element(by.id('field_lastProblemDate'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setProblemCountInput(problemCount) {
        await this.problemCountInput.sendKeys(problemCount);
    }

    async getProblemCountInput() {
        return this.problemCountInput.getAttribute('value');
    }

    async setSysStatusSelect(sysStatus) {
        await this.sysStatusSelect.sendKeys(sysStatus);
    }

    async getSysStatusSelect() {
        return this.sysStatusSelect.element(by.css('option:checked')).getText();
    }

    async sysStatusSelectLastOption() {
        await this.sysStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setLastProblemDateInput(lastProblemDate) {
        await this.lastProblemDateInput.sendKeys(lastProblemDate);
    }

    async getLastProblemDateInput() {
        return this.lastProblemDateInput.getAttribute('value');
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

export class AppDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-app-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-app'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
