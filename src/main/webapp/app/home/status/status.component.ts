import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { StatusService } from 'app/home/status/status.service';
import { AppStatus } from 'app/shared/model/status.model';

@Component({
    selector: 'jhi-status',
    templateUrl: './status.component.html',
    styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {
    sysMessage: string;
    sysMessageColor: string;
    msgColor: string;
    msgDetail: string;
    probCount: string;
    appStatus: any;
    imageColor: any;

    constructor(private jhiAlertService: JhiAlertService, private statusService: StatusService) {}

    ngOnInit() {
        this.getAppStatus();
    }

    private getAppStatus() {
        // Comment out the code below before running npm test e2e
        // setTimeout(() => {
        //     this.getAppStatus();
        // }, 10000);

        this.statusService.getAppStatus().subscribe(
            (res: HttpResponse<AppStatus>) => {
                this.sysMessage = res.body.sysMessage;
                this.sysMessageColor = res.body.sysMessageColor;
                this.msgDetail = res.body.msgDetail;
                this.msgColor = res.body.msgColor;
                this.probCount = res.body.probCount;
                this.appStatus = res.body.appStatus.toString().toLowerCase();
                this.imageColor = require('../../../content/images/' + this.appStatus + '.png');
                this.statusService.incidentSwitch.emit(this.appStatus);
            },
            (res: HttpErrorResponse) => {
                this.onError(res);
            }
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error, null, null);
    }
}
