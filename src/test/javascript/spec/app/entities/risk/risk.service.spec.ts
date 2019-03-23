/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RiskService } from 'app/entities/risk/risk.service';
import { IRisk, Risk, IssueStatus, Priority } from 'app/shared/model/risk.model';

describe('Service Tests', () => {
    describe('Risk Service', () => {
        let injector: TestBed;
        let service: RiskService;
        let httpMock: HttpTestingController;
        let elemDefault: IRisk;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RiskService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Risk(0, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', IssueStatus.OPEN, Priority.LOW, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        openedAt: currentDate.format(DATE_FORMAT),
                        closedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Risk', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        openedAt: currentDate.format(DATE_FORMAT),
                        closedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        openedAt: currentDate,
                        closedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Risk(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Risk', async () => {
                const returnedFromService = Object.assign(
                    {
                        openedAt: currentDate.format(DATE_FORMAT),
                        title: 'BBBBBB',
                        description: 'BBBBBB',
                        mitigation: 'BBBBBB',
                        riskStatus: 'BBBBBB',
                        priority: 'BBBBBB',
                        closedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        openedAt: currentDate,
                        closedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            // it('should return a list of Risk', async () => {
            //     const returnedFromService = Object.assign(
            //         {
            //             openedAt: currentDate.format(DATE_FORMAT),
            //             title: 'BBBBBB',
            //             description: 'BBBBBB',
            //             mitigation: 'BBBBBB',
            //             riskStatus: 'BBBBBB',
            //             priority: 'BBBBBB',
            //             closedAt: currentDate.format(DATE_FORMAT)
            //         },
            //         elemDefault
            //     );
            //     const expected = Object.assign(
            //         {
            //             openedAt: currentDate,
            //             closedAt: currentDate
            //         },
            //         returnedFromService
            //     );
            //     service
            //         .query(expected)
            //         .pipe(
            //             take(1),
            //             map(resp => resp.body)
            //         )
            //         .subscribe(body => expect(body).toContainEqual(expected));
            //     const req = httpMock.expectOne({ method: 'GET' });
            //     req.flush(JSON.stringify([returnedFromService]));
            //     httpMock.verify();
            // });

            it('should delete a Risk', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
