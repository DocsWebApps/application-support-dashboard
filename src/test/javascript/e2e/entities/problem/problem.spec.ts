/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';
import { ProblemComponentsPage, ProblemDeleteDialog, ProblemUpdatePage } from './problem.page-object';
import { ProblemUpdatesComponentsPage, ProblemUpdatesDeleteDialog, ProblemUpdatesUpdatePage } from './problem-updates.page-object';

const expect = chai.expect;

describe('Problem e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let problemUpdatePage: ProblemUpdatePage;
    let problemComponentsPage: ProblemComponentsPage;
    let problemDeleteDialog: ProblemDeleteDialog;
    let problemUpdatesUpdatePage: ProblemUpdatesUpdatePage;
    let problemUpdatesComponentsPage: ProblemUpdatesComponentsPage;
    let problemUpdatesDeleteDialog: ProblemUpdatesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Problems', async () => {
        await navBarPage.goToEntity('problem');
        problemComponentsPage = new ProblemComponentsPage();
        await browser.wait(ec.visibilityOf(problemComponentsPage.title), 5000);
        expect(await problemComponentsPage.getTitle()).to.eq('Problem List');
    });

    it('should load create Problem page', async () => {
        await problemComponentsPage.clickOnCreateButton();
        problemUpdatePage = new ProblemUpdatePage();
        expect(await problemUpdatePage.getPageTitle()).to.eq('Create or edit a Problem');
        await problemUpdatePage.cancel();
    });

    it('should create and save Problems', async () => {
        const nbButtonsBeforeCreate = await problemComponentsPage.countDeleteButtons();
        await problemComponentsPage.clickOnCreateButton();
        await promise.all([
            problemUpdatePage.setOpenedAtInput('2000-12-31'),
            problemUpdatePage.setTitleInput('title'),
            problemUpdatePage.setStatementInput('statement'),
            problemUpdatePage.probStatusSelectLastOption(),
            problemUpdatePage.prioritySelectLastOption(),
            problemUpdatePage.setClosedAtInput('2000-12-31'),
            problemUpdatePage.riskRecSelectLastOption()
        ]);
        expect(await problemUpdatePage.getOpenedAtInput()).to.eq('2000-12-31');
        expect(await problemUpdatePage.getTitleInput()).to.eq('title');
        expect(await problemUpdatePage.getStatementInput()).to.eq('statement');
        expect(await problemUpdatePage.getClosedAtInput()).to.eq('2000-12-31');
        await problemUpdatePage.save();
        expect(await problemUpdatePage.getSaveButton().isPresent()).to.be.false;
        expect(await problemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should load ProblemUpdates', async () => {
        problemComponentsPage = new ProblemComponentsPage();
        await problemComponentsPage.clickOnViewButton();
        problemUpdatesComponentsPage = new ProblemUpdatesComponentsPage();
        await browser.wait(ec.visibilityOf(problemUpdatesComponentsPage.title), 5000);
        expect(await problemUpdatesComponentsPage.getTitle()).to.eq('Problem Updates');
    });

    it('should load create ProblemUpdates page', async () => {
        problemUpdatesComponentsPage = new ProblemUpdatesComponentsPage();
        await problemUpdatesComponentsPage.clickOnCreateButton();
        problemUpdatesUpdatePage = new ProblemUpdatesUpdatePage();
        expect(await problemUpdatesUpdatePage.getPageTitle()).to.eq('Create or edit a Problem Updates');
        await problemUpdatesUpdatePage.cancel();
    });

    it('should create and save ProblemUpdates', async () => {
        const nbButtonsBeforeCreate = await problemUpdatesComponentsPage.countDeleteButtons();

        await problemUpdatesComponentsPage.clickOnCreateButton();
        await promise.all([
            problemUpdatesUpdatePage.setUpdatedAtInput('2000-12-31'),
            problemUpdatesUpdatePage.setUpdateTextInput('updateText'),
            problemUpdatesUpdatePage.probUpdateSelectLastOption()
        ]);
        expect(await problemUpdatesUpdatePage.getUpdatedAtInput()).to.eq('2000-12-31');
        expect(await problemUpdatesUpdatePage.getUpdateTextInput()).to.eq('updateText');
        await problemUpdatesUpdatePage.save();
        expect(await problemUpdatesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await problemUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProblemUpdates', async () => {
        const nbButtonsBeforeDelete = await problemUpdatesComponentsPage.countDeleteButtons();
        await problemUpdatesComponentsPage.clickOnLastDeleteButton();

        problemUpdatesDeleteDialog = new ProblemUpdatesDeleteDialog();
        expect(await problemUpdatesDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Problem Updates?');
        await problemUpdatesDeleteDialog.clickOnConfirmButton();

        expect(await problemUpdatesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    it('should delete last Problem', async () => {
        const nbButtonsBeforeDelete = await problemComponentsPage.countDeleteButtons();
        await problemComponentsPage.clickOnLastDeleteButton();

        problemDeleteDialog = new ProblemDeleteDialog();
        expect(await problemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Problem?');
        await problemDeleteDialog.clickOnConfirmButton();

        expect(await problemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
