/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AppComponentsPage, AppDeleteDialog, AppUpdatePage } from './app.page-object';

const expect = chai.expect;

describe('App e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let appUpdatePage: AppUpdatePage;
    let appComponentsPage: AppComponentsPage;
    let appDeleteDialog: AppDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should clear any stray app', async () => {
        await navBarPage.goToEntity('app');
        appComponentsPage = new AppComponentsPage();
        const countCreateButtons = await appComponentsPage.countCreateButtons();
        if (countCreateButtons !== 1) {
            await appComponentsPage.clickOnLastDeleteButton();
            appDeleteDialog = new AppDeleteDialog();
            expect(await appDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this App?');
            await appDeleteDialog.clickOnConfirmButton();
        }
    });

    it('should load Apps', async () => {
        await navBarPage.goToEntity('app');
        appComponentsPage = new AppComponentsPage();
        await browser.wait(ec.visibilityOf(appComponentsPage.title), 5000);
        expect(await appComponentsPage.getTitle()).to.eq('Application Details');
    });

    it('should load create App page', async () => {
        await appComponentsPage.clickOnCreateButton();
        appUpdatePage = new AppUpdatePage();
        expect(await appUpdatePage.getPageTitle()).to.eq('Create or edit your Application Details');
        await appUpdatePage.cancel();
    });

    it('should create and save Apps', async () => {
        const nbButtonsBeforeCreate = await appComponentsPage.countDeleteButtons();

        await appComponentsPage.clickOnCreateButton();
        await promise.all([
            appUpdatePage.setNameInput('{Application Name}'),
            appUpdatePage.setProblemCountInput('5'),
            appUpdatePage.sysStatusSelectLastOption(),
            appUpdatePage.setLastProblemDateInput('2000-12-31')
        ]);
        expect(await appUpdatePage.getNameInput()).to.eq('{Application Name}');
        expect(await appUpdatePage.getProblemCountInput()).to.eq('5');
        expect(await appUpdatePage.getLastProblemDateInput()).to.eq('2000-12-31');
        await appUpdatePage.save();
        expect(await appUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await appComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last App', async () => {
        const nbButtonsBeforeDelete = await appComponentsPage.countDeleteButtons();
        await appComponentsPage.clickOnLastDeleteButton();

        appDeleteDialog = new AppDeleteDialog();
        expect(await appDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this App?');
        await appDeleteDialog.clickOnConfirmButton();

        expect(await appComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    it('should create a dummy app for further tests', async () => {
        const nbButtonsBeforeCreate = await appComponentsPage.countDeleteButtons();

        await appComponentsPage.clickOnCreateButton();
        await promise.all([
            appUpdatePage.setNameInput('DocsWebApps Application'),
            appUpdatePage.setProblemCountInput('5'),
            appUpdatePage.sysStatusSelectLastOption(),
            appUpdatePage.setLastProblemDateInput('2000-12-31')
        ]);
        expect(await appUpdatePage.getNameInput()).to.eq('{Application Name}');
        expect(await appUpdatePage.getProblemCountInput()).to.eq('5');
        expect(await appUpdatePage.getLastProblemDateInput()).to.eq('2000-12-31');
        await appUpdatePage.save();
        expect(await appUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await appComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
