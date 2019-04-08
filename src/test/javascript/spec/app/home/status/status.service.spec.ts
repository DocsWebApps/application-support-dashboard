/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StatusService } from 'app/home/status/status.service';
import { take } from 'rxjs/operators';
import { AppStatus } from 'app/shared/model/status.model';
import { SystemStatus } from 'app/shared/model/app.model';

describe('Service Tests', () => {
    describe('Status Service', () => {
        let injector: TestBed;
        let service: StatusService;
        let httpMock: HttpTestingController;
        let elemDefault: AppStatus;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StatusService);
            httpMock = injector.get(HttpTestingController);
            elemDefault = new AppStatus('AAA', 'BBB', 'CCC', 'DDD', SystemStatus.GREEN, '0');
        });

        describe('Service methods', async () => {
            it('Should do a test', () => {
                expect(1).toEqual(1);
            });

            it('should get stats', async () => {
                const returnedFromService = Object.assign(elemDefault);

                service
                    .getAppStatus()
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
