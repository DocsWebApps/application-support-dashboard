/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { BannerService } from 'app/home/banner/banner.service';
import { IIncident, Incident, IssueStatus, Severity } from 'app/shared/model/incident.model';
import { take } from 'rxjs/operators';
import { Stats } from 'app/shared/model/stats.model';

describe('Service Tests', () => {
    describe('Banner Service', () => {
        let injector: TestBed;
        let service: BannerService;
        let httpMock: HttpTestingController;
        let currentDate: moment.Moment;
        let elemDefault: IIncident;
        let elemDefault1: Stats;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BannerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();
            elemDefault = new Incident(0, currentDate, 'AAAAAAA', Severity.P1, IssueStatus.OPEN, currentDate);
            elemDefault1 = new Stats(1, 2, '3', '4');
        });

        describe('Service methods', async () => {
            it('should get an incident', async () => {
                const returnedFromService = Object.assign(elemDefault);

                service
                    .getBannerIncident()
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should get stats', async () => {
                const returnedFromService = Object.assign(elemDefault1);

                service
                    .getBannerStats()
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault1 }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
