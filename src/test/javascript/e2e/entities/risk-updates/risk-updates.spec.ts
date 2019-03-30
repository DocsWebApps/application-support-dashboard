/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RiskUpdatesComponentsPage, RiskUpdatesDeleteDialog, RiskUpdatesUpdatePage } from '../risk/risk-updates.page-object';

const expect = chai.expect;

describe('RiskUpdates e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
