import { element, by, ElementFinder } from 'protractor';

export class IncidentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-incident div table .btn-danger'));
    title = element.all(by.css('jhi-incident div h2#page-heading span')).first();

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

export class IncidentUpdatePage {
    pageTitle = element(by.id('jhi-incident-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    openedAtInput = element(by.id('field_openedAt'));
    descriptionInput = element(by.id('field_description'));
    severitySelect = element(by.id('field_severity'));
    incidentStatusSelect = element(by.id('field_incidentStatus'));
    closedAtInput = element(by.id('field_closedAt'));
    probRecSelect = element(by.id('field_probRec'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setOpenedAtInput(openedAt) {
        await this.openedAtInput.sendKeys(openedAt);
    }

    async getOpenedAtInput() {
        return this.openedAtInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setSeveritySelect(severity) {
        await this.severitySelect.sendKeys(severity);
    }

    async getSeveritySelect() {
        return this.severitySelect.element(by.css('option:checked')).getText();
    }

    async severitySelectLastOption() {
        await this.severitySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setIncidentStatusSelect(incidentStatus) {
        await this.incidentStatusSelect.sendKeys(incidentStatus);
    }

    async getIncidentStatusSelect() {
        return this.incidentStatusSelect.element(by.css('option:checked')).getText();
    }

    async incidentStatusSelectLastOption() {
        await this.incidentStatusSelect
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

    async probRecSelectLastOption() {
        await this.probRecSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async probRecSelectOption(option) {
        await this.probRecSelect.sendKeys(option);
    }

    getProbRecSelect(): ElementFinder {
        return this.probRecSelect;
    }

    async getProbRecSelectedOption() {
        return this.probRecSelect.element(by.css('option:checked')).getText();
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
