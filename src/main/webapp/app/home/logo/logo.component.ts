import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { NameService } from 'app/shared/services/name.service';
import { JhiAlertService } from 'ng-jhipster';
import { Name } from 'app/shared/model/name.model';

@Component({
    selector: 'jhi-logo',
    templateUrl: './logo.component.html',
    styleUrls: ['./logo.component.scss']
})
export class LogoComponent implements OnInit {
    name: string;

    constructor(private nameService: NameService, private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.getName();
    }

    private getName() {
        this.nameService.getName().subscribe(
            (res: HttpResponse<Name>) => {
                this.name = res.body.name;
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
