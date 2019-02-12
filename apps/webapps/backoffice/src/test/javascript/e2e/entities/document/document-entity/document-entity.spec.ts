/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { DocumentEntityComponentsPage, DocumentEntityDeleteDialog, DocumentEntityUpdatePage } from './document-entity.page-object';

const expect = chai.expect;

describe('DocumentEntity e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentEntityUpdatePage: DocumentEntityUpdatePage;
    let documentEntityComponentsPage: DocumentEntityComponentsPage;
    let documentEntityDeleteDialog: DocumentEntityDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DocumentEntities', async () => {
        await navBarPage.goToEntity('document-entity');
        documentEntityComponentsPage = new DocumentEntityComponentsPage();
        await browser.wait(ec.visibilityOf(documentEntityComponentsPage.title), 5000);
        expect(await documentEntityComponentsPage.getTitle()).to.eq('backofficeApp.documentDocumentEntity.home.title');
    });

    it('should load create DocumentEntity page', async () => {
        await documentEntityComponentsPage.clickOnCreateButton();
        documentEntityUpdatePage = new DocumentEntityUpdatePage();
        expect(await documentEntityUpdatePage.getPageTitle()).to.eq('backofficeApp.documentDocumentEntity.home.createOrEditLabel');
        await documentEntityUpdatePage.cancel();
    });

    it('should create and save DocumentEntities', async () => {
        const nbButtonsBeforeCreate = await documentEntityComponentsPage.countDeleteButtons();

        await documentEntityComponentsPage.clickOnCreateButton();
        await promise.all([documentEntityUpdatePage.setTextInput('text'), documentEntityUpdatePage.setResumeInput('resume')]);
        expect(await documentEntityUpdatePage.getTextInput()).to.eq('text');
        expect(await documentEntityUpdatePage.getResumeInput()).to.eq('resume');
        await documentEntityUpdatePage.save();
        expect(await documentEntityUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentEntityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DocumentEntity', async () => {
        const nbButtonsBeforeDelete = await documentEntityComponentsPage.countDeleteButtons();
        await documentEntityComponentsPage.clickOnLastDeleteButton();

        documentEntityDeleteDialog = new DocumentEntityDeleteDialog();
        expect(await documentEntityDeleteDialog.getDialogTitle()).to.eq('backofficeApp.documentDocumentEntity.delete.question');
        await documentEntityDeleteDialog.clickOnConfirmButton();

        expect(await documentEntityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
