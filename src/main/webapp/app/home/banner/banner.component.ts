import { Component, OnInit } from '@angular/core';
import { BannerService } from 'app/home/banner/banner.service';
import { JhiAlertService } from 'ng-jhipster';
import { StatusService } from 'app/home/status/status.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Stats } from 'app/shared/model/stats.model';
import { IIncident } from 'app/shared/model/incident.model';

@Component({
    selector: 'jhi-banner',
    templateUrl: './banner.component.html',
    styleUrls: ['./banner.component.scss']
})
export class BannerComponent implements OnInit {
    incidentSwitch = true;
    incidentStatus: any;
    incidentDescription: any;
    p3Count: any;
    p4Count: any;
    problemCount: any;
    riskCount: any;

    constructor(private statusService: StatusService, private bannerService: BannerService, private jhiAlertService: JhiAlertService) {
        this.statusService.incidentSwitch.subscribe((appStatus: string) => {
            if (appStatus === 'green') {
                this.bannerService.getBannerStats().subscribe(
                    (res: HttpResponse<Stats>) => {
                        this.p3Count = res.body.p3Count;
                        this.p4Count = res.body.p4Count;
                        this.problemCount = res.body.problemCount;
                        this.riskCount = res.body.riskCount;
                        this.incidentSwitch = true;
                    },
                    (res: HttpErrorResponse) => {
                        this.onError(res);
                    }
                );
            } else {
                this.bannerService.getBannerIncident().subscribe(
                    (res: HttpResponse<IIncident>) => {
                        this.incidentStatus = res.body.severity;
                        this.incidentDescription = res.body.description;
                        this.incidentSwitch = false;
                    },
                    (res: HttpErrorResponse) => {
                        this.onError(res);
                    }
                );
            }
        });
    }

    ngOnInit() {}

    private onError(error) {
        this.jhiAlertService.error(error, null, null);
    }
}
