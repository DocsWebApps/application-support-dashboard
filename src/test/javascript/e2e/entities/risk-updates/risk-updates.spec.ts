/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RiskUpdatesComponentsPage, RiskUpdatesDeleteDialog, RiskUpdatesUpdatePage } from './risk-updates.page-object';

const expect = chai.expect;

describe('RiskUpdates e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let riskUpdatesUpdatePage: RiskUpdatesUpdatePage;
    let riskUpdatesComponentsPage: RiskUpdatesComponentsPage;
    /*let riskUpdatesDeleteDialog: RiskUpdatesDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RiskUpdates', async () => {
        await navBarPage.goToEntity('risk-updates');
        riskUpdatesComponentsPage = new RiskUpdatesComponentsPage();
        await browser.wait(ec.visibilityOf(riskUpdatesComponentsPage.title), 5000);
        expect(await riskUpdatesComponentsPage.getTitle()).to.eq('Risk Updates');
    });

    it('should load create RiskUpdates page', async () => {
        await riskUpdatesComponentsPage.clickOnCreateButton();
        riskUpdatesUpdatePage = new RiskUpdatesUpdatePage();
        expect(await riskUpdatesUpdatePage.getPageTitle()).to.eq('Create or edit a Risk Updates');
        await riskUpdatesUpdatePage.cancel();
    });

    /* it('should create and save RiskUpdates', async () => {
        const nbButtonsBeforeCreate = await riskUpdatesComponentsPage.countDeleteButtons();

        await riskUpdatesComponentsPage.clickOnCreateButton();
        await promise.all([
            riskUpdatesUpdatePage.setUpdatedAtInput('2000-12-31'),
            riskUpdatesUpdatePage.setUpdateTextInput('updateText'),
            riskUpdatesUpdatePage.riskkUpdateSelectLastOption(),
        ]);
        expect(await riskUpdatesUpdatePage.getUpdatedAtInput()).to.eq('2000-12-31');
        expect(await riskUpdatesUpdatePage.getUpdateTextInput()).to.eq('updateText');
        await riskUpdatesUpdatePage.save();
        expect(await riskUpdatesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await riskUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last RiskUpdates', async () => {
        const nbButtonsBeforeDelete = await riskUpdatesComponentsPage.countDeleteButtons();
        await riskUpdatesComponentsPage.clickOnLastDeleteButton();

        riskUpdatesDeleteDialog = new RiskUpdatesDeleteDialog();
        expect(await riskUpdatesDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Risk Updates?');
        await riskUpdatesDeleteDialog.clickOnConfirmButton();

        expect(await riskUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
