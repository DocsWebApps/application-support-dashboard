/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';
import { IncidentComponentsPage, IncidentDeleteDialog, IncidentUpdatePage } from './incident.page-object';
import { IncidentUpdatesComponentsPage, IncidentUpdatesDeleteDialog, IncidentUpdatesUpdatePage } from './incident-updates.page-object';

const expect = chai.expect;

describe('Incident e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let incidentUpdatePage: IncidentUpdatePage;
    let incidentComponentsPage: IncidentComponentsPage;
    let incidentDeleteDialog: IncidentDeleteDialog;
    let incidentUpdatesUpdatePage: IncidentUpdatesUpdatePage;
    let incidentUpdatesComponentsPage: IncidentUpdatesComponentsPage;
    let incidentUpdatesDeleteDialog: IncidentUpdatesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Incidents', async () => {
        await navBarPage.goToEntity('incident');
        incidentComponentsPage = new IncidentComponentsPage();
        await browser.wait(ec.visibilityOf(incidentComponentsPage.title), 5000);
        expect(await incidentComponentsPage.getTitle()).to.eq('Incident List');
    });

    it('should load create Incident page', async () => {
        await incidentComponentsPage.clickOnCreateButton();
        incidentUpdatePage = new IncidentUpdatePage();
        expect(await incidentUpdatePage.getPageTitle()).to.eq('Create or edit a Incident');
        await incidentUpdatePage.cancel();
    });

    it('should create and save Incidents', async () => {
        const nbButtonsBeforeCreate = await incidentComponentsPage.countDeleteButtons();

        await incidentComponentsPage.clickOnCreateButton();
        await promise.all([
            incidentUpdatePage.setOpenedAtInput('2000-12-31'),
            incidentUpdatePage.setDescriptionInput('description'),
            incidentUpdatePage.severitySelectLastOption(),
            incidentUpdatePage.incidentStatusSelectLastOption(),
            incidentUpdatePage.setClosedAtInput('2000-12-31'),
            incidentUpdatePage.probRecSelectLastOption()
        ]);
        expect(await incidentUpdatePage.getOpenedAtInput()).to.eq('2000-12-31');
        expect(await incidentUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await incidentUpdatePage.getClosedAtInput()).to.eq('2000-12-31');
        await incidentUpdatePage.save();
        expect(await incidentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await incidentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should load IncidentUpdates', async () => {
        incidentComponentsPage = new IncidentComponentsPage();
        await incidentComponentsPage.clickOnViewButton();
        incidentUpdatesComponentsPage = new IncidentUpdatesComponentsPage();
        await browser.wait(ec.visibilityOf(incidentUpdatesComponentsPage.title), 5000);
        expect(await incidentUpdatesComponentsPage.getTitle()).to.eq('Incident Updates');
    });

    it('should load create IncidentUpdates page', async () => {
        await incidentUpdatesComponentsPage.clickOnCreateButton();
        incidentUpdatesUpdatePage = new IncidentUpdatesUpdatePage();
        expect(await incidentUpdatesUpdatePage.getPageTitle()).to.eq('Create or edit an Incident Update');
        await incidentUpdatesUpdatePage.cancel();
    });

    it('should create and save IncidentUpdates', async () => {
        const nbButtonsBeforeCreate = await incidentUpdatesComponentsPage.countDeleteButtons();

        await incidentUpdatesComponentsPage.clickOnCreateButton();
        await promise.all([
            incidentUpdatesUpdatePage.setUpdatedAtInput('2000-12-31'),
            incidentUpdatesUpdatePage.setUpdateTextInput('updateText')
        ]);
        expect(await incidentUpdatesUpdatePage.getUpdatedAtInput()).to.eq('2000-12-31');
        expect(await incidentUpdatesUpdatePage.getUpdateTextInput()).to.eq('updateText');
        await incidentUpdatesUpdatePage.save();
        expect(await incidentUpdatesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await incidentUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last IncidentUpdates', async () => {
        const nbButtonsBeforeDelete = await incidentUpdatesComponentsPage.countDeleteButtons();
        await incidentUpdatesComponentsPage.clickOnLastDeleteButton();
        incidentUpdatesDeleteDialog = new IncidentUpdatesDeleteDialog();
        expect(await incidentUpdatesDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Incident Updates?');
        await incidentUpdatesDeleteDialog.clickOnConfirmButton();
        expect(await incidentUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    it('should delete last Incident', async () => {
        await navBarPage.goToEntity('incident');
        const nbButtonsBeforeDelete = await incidentComponentsPage.countDeleteButtons();
        await incidentComponentsPage.clickOnLastDeleteButton();
        incidentDeleteDialog = new IncidentDeleteDialog();
        expect(await incidentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Incident?');
        await incidentDeleteDialog.clickOnConfirmButton();
        expect(await incidentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
