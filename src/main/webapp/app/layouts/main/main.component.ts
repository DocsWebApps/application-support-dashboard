import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, NavigationError } from '@angular/router';

import { Title } from '@angular/platform-browser';
import { NameService } from 'app/shared/services/name.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Name } from 'app/shared/model/name.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    currentRoute: string;

    constructor(
        private titleService: Title,
        private router: Router,
        private nameService: NameService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.router.events.subscribe(event => {
            this.currentRoute = this.router.url;
            if (event instanceof NavigationEnd) {
                this.nameService.getName().subscribe(
                    (res: HttpResponse<Name>) => {
                        this.titleService.setTitle(res.body.name);
                    },
                    (res: HttpErrorResponse) => {
                        this.onError(res);
                    }
                );
            }
            if (event instanceof NavigationError && event.error.status === 404) {
                this.router.navigate(['/404']);
            }
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error, null, null);
    }
}
