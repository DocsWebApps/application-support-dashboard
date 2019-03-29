import { element, by, ElementFinder } from 'protractor';

export class ProblemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    viewButton = element.all(by.css('view-button')).first();
    deleteButtons = element.all(by.css('jhi-problem div table .btn-danger'));
    title = element.all(by.css('jhi-problem div h2#page-heading span')).first();

    async clickOnViewButton() {
        await this.viewButton.click();
    }

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

export class ProblemUpdatePage {
    pageTitle = element(by.id('jhi-problem-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    openedAtInput = element(by.id('field_openedAt'));
    titleInput = element(by.id('field_title'));
    statementInput = element(by.id('field_statement'));
    probStatusSelect = element(by.id('field_probStatus'));
    prioritySelect = element(by.id('field_priority'));
    closedAtInput = element(by.id('field_closedAt'));
    riskRecSelect = element(by.id('field_riskRec'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setOpenedAtInput(openedAt) {
        await this.openedAtInput.sendKeys(openedAt);
    }

    async getOpenedAtInput() {
        return this.openedAtInput.getAttribute('value');
    }

    async setTitleInput(title) {
        await this.titleInput.sendKeys(title);
    }

    async getTitleInput() {
        return this.titleInput.getAttribute('value');
    }

    async setStatementInput(statement) {
        await this.statementInput.sendKeys(statement);
    }

    async getStatementInput() {
        return this.statementInput.getAttribute('value');
    }

    async setProbStatusSelect(probStatus) {
        await this.probStatusSelect.sendKeys(probStatus);
    }

    async getProbStatusSelect() {
        return this.probStatusSelect.element(by.css('option:checked')).getText();
    }

    async probStatusSelectLastOption() {
        await this.probStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setPrioritySelect(priority) {
        await this.prioritySelect.sendKeys(priority);
    }

    async getPrioritySelect() {
        return this.prioritySelect.element(by.css('option:checked')).getText();
    }

    async prioritySelectLastOption() {
        await this.prioritySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setClosedAtInput(closedAt) {
        await this.closedAtInput.sendKeys(closedAt);
    }

    async getClosedAtInput() {
        return this.closedAtInput.getAttribute('value');
    }

    async riskRecSelectLastOption() {
        await this.riskRecSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async riskRecSelectOption(option) {
        await this.riskRecSelect.sendKeys(option);
    }

    getRiskRecSelect(): ElementFinder {
        return this.riskRecSelect;
    }

    async getRiskRecSelectedOption() {
        return this.riskRecSelect.element(by.css('option:checked')).getText();
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

export class ProblemDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-problem-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-problem'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
