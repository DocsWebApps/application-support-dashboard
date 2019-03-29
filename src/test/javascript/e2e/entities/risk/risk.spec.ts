/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RiskComponentsPage, RiskDeleteDialog, RiskUpdatePage } from './risk.page-object';

const expect = chai.expect;

describe('Risk e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let riskUpdatePage: RiskUpdatePage;
    let riskComponentsPage: RiskComponentsPage;
    let riskDeleteDialog: RiskDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Risks', async () => {
        await navBarPage.goToEntity('risk');
        riskComponentsPage = new RiskComponentsPage();
        await browser.wait(ec.visibilityOf(riskComponentsPage.title), 5000);
        expect(await riskComponentsPage.getTitle()).to.eq('Risk List');
    });

    it('should load create Risk page', async () => {
        await riskComponentsPage.clickOnCreateButton();
        riskUpdatePage = new RiskUpdatePage();
        expect(await riskUpdatePage.getPageTitle()).to.eq('Create or edit a Risk');
        await riskUpdatePage.cancel();
    });

    it('should create and save Risks', async () => {
        const nbButtonsBeforeCreate = await riskComponentsPage.countDeleteButtons();

        await riskComponentsPage.clickOnCreateButton();
        await promise.all([
            riskUpdatePage.setOpenedAtInput('2000-12-31'),
            riskUpdatePage.setTitleInput('title'),
            riskUpdatePage.setDescriptionInput('description'),
            riskUpdatePage.setMitigationInput('mitigation'),
            riskUpdatePage.riskStatusSelectLastOption(),
            riskUpdatePage.prioritySelectLastOption(),
            riskUpdatePage.setClosedAtInput('2000-12-31')
        ]);
        expect(await riskUpdatePage.getOpenedAtInput()).to.eq('2000-12-31');
        expect(await riskUpdatePage.getTitleInput()).to.eq('title');
        expect(await riskUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await riskUpdatePage.getMitigationInput()).to.eq('mitigation');
        expect(await riskUpdatePage.getClosedAtInput()).to.eq('2000-12-31');
        await riskUpdatePage.save();
        expect(await riskUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await riskComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Risk', async () => {
        const nbButtonsBeforeDelete = await riskComponentsPage.countDeleteButtons();
        await riskComponentsPage.clickOnLastDeleteButton();

        riskDeleteDialog = new RiskDeleteDialog();
        expect(await riskDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Risk?');
        await riskDeleteDialog.clickOnConfirmButton();

        expect(await riskComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
