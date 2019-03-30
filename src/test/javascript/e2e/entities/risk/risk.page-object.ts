import { element, by, ElementFinder } from 'protractor';

export class RiskComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    viewButton = element.all(by.id('view-button')).first();
    deleteButtons = element.all(by.css('jhi-risk div table .btn-danger'));
    title = element.all(by.css('jhi-risk div h2#page-heading span')).first();

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

export class RiskUpdatePage {
    pageTitle = element(by.id('jhi-risk-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    openedAtInput = element(by.id('field_openedAt'));
    titleInput = element(by.id('field_title'));
    descriptionInput = element(by.id('field_description'));
    mitigationInput = element(by.id('field_mitigation'));
    riskStatusSelect = element(by.id('field_riskStatus'));
    prioritySelect = element(by.id('field_priority'));
    closedAtInput = element(by.id('field_closedAt'));

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

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setMitigationInput(mitigation) {
        await this.mitigationInput.sendKeys(mitigation);
    }

    async getMitigationInput() {
        return this.mitigationInput.getAttribute('value');
    }

    async setRiskStatusSelect(riskStatus) {
        await this.riskStatusSelect.sendKeys(riskStatus);
    }

    async getRiskStatusSelect() {
        return this.riskStatusSelect.element(by.css('option:checked')).getText();
    }

    async riskStatusSelectLastOption() {
        await this.riskStatusSelect
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

export class RiskDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-risk-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-risk'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
