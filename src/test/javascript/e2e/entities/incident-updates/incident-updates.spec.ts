/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IncidentUpdatesComponentsPage, IncidentUpdatesDeleteDialog, IncidentUpdatesUpdatePage } from './incident-updates.page-object';

const expect = chai.expect;

describe('IncidentUpdates e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let incidentUpdatesUpdatePage: IncidentUpdatesUpdatePage;
    let incidentUpdatesComponentsPage: IncidentUpdatesComponentsPage;
    /*let incidentUpdatesDeleteDialog: IncidentUpdatesDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load IncidentUpdates', async () => {
        await navBarPage.goToEntity('incident-updates');
        incidentUpdatesComponentsPage = new IncidentUpdatesComponentsPage();
        await browser.wait(ec.visibilityOf(incidentUpdatesComponentsPage.title), 5000);
        expect(await incidentUpdatesComponentsPage.getTitle()).to.eq('Incident Updates');
    });

    it('should load create IncidentUpdates page', async () => {
        await incidentUpdatesComponentsPage.clickOnCreateButton();
        incidentUpdatesUpdatePage = new IncidentUpdatesUpdatePage();
        expect(await incidentUpdatesUpdatePage.getPageTitle()).to.eq('Create or edit a Incident Updates');
        await incidentUpdatesUpdatePage.cancel();
    });

    /* it('should create and save IncidentUpdates', async () => {
        const nbButtonsBeforeCreate = await incidentUpdatesComponentsPage.countDeleteButtons();

        await incidentUpdatesComponentsPage.clickOnCreateButton();
        await promise.all([
            incidentUpdatesUpdatePage.setUpdatedAtInput('2000-12-31'),
            incidentUpdatesUpdatePage.setUpdateTextInput('updateText'),
            incidentUpdatesUpdatePage.inUpdateSelectLastOption(),
        ]);
        expect(await incidentUpdatesUpdatePage.getUpdatedAtInput()).to.eq('2000-12-31');
        expect(await incidentUpdatesUpdatePage.getUpdateTextInput()).to.eq('updateText');
        await incidentUpdatesUpdatePage.save();
        expect(await incidentUpdatesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await incidentUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last IncidentUpdates', async () => {
        const nbButtonsBeforeDelete = await incidentUpdatesComponentsPage.countDeleteButtons();
        await incidentUpdatesComponentsPage.clickOnLastDeleteButton();

        incidentUpdatesDeleteDialog = new IncidentUpdatesDeleteDialog();
        expect(await incidentUpdatesDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Incident Updates?');
        await incidentUpdatesDeleteDialog.clickOnConfirmButton();

        expect(await incidentUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
