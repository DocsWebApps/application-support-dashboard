/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProblemUpdatesComponentsPage, ProblemUpdatesDeleteDialog, ProblemUpdatesUpdatePage } from './problem-updates.page-object';

const expect = chai.expect;

describe('ProblemUpdates e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let problemUpdatesUpdatePage: ProblemUpdatesUpdatePage;
    let problemUpdatesComponentsPage: ProblemUpdatesComponentsPage;
    /*let problemUpdatesDeleteDialog: ProblemUpdatesDeleteDialog;*/

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProblemUpdates', async () => {
        await navBarPage.goToEntity('problem-updates');
        problemUpdatesComponentsPage = new ProblemUpdatesComponentsPage();
        await browser.wait(ec.visibilityOf(problemUpdatesComponentsPage.title), 5000);
        expect(await problemUpdatesComponentsPage.getTitle()).to.eq('Problem Updates');
    });

    it('should load create ProblemUpdates page', async () => {
        await problemUpdatesComponentsPage.clickOnCreateButton();
        problemUpdatesUpdatePage = new ProblemUpdatesUpdatePage();
        expect(await problemUpdatesUpdatePage.getPageTitle()).to.eq('Create or edit a Problem Updates');
        await problemUpdatesUpdatePage.cancel();
    });

    /* it('should create and save ProblemUpdates', async () => {
        const nbButtonsBeforeCreate = await problemUpdatesComponentsPage.countDeleteButtons();

        await problemUpdatesComponentsPage.clickOnCreateButton();
        await promise.all([
            problemUpdatesUpdatePage.setUpdatedAtInput('2000-12-31'),
            problemUpdatesUpdatePage.setUpdateTextInput('updateText'),
            problemUpdatesUpdatePage.probUpdateSelectLastOption(),
        ]);
        expect(await problemUpdatesUpdatePage.getUpdatedAtInput()).to.eq('2000-12-31');
        expect(await problemUpdatesUpdatePage.getUpdateTextInput()).to.eq('updateText');
        await problemUpdatesUpdatePage.save();
        expect(await problemUpdatesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await problemUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last ProblemUpdates', async () => {
        const nbButtonsBeforeDelete = await problemUpdatesComponentsPage.countDeleteButtons();
        await problemUpdatesComponentsPage.clickOnLastDeleteButton();

        problemUpdatesDeleteDialog = new ProblemUpdatesDeleteDialog();
        expect(await problemUpdatesDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Problem Updates?');
        await problemUpdatesDeleteDialog.clickOnConfirmButton();

        expect(await problemUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
