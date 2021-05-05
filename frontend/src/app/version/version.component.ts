import { Component, OnInit } from '@angular/core';
import { CoreService } from 'gen/api/core.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-version',
  template: '<p>{{version | async }}</p>'
})
export class VersionComponent implements OnInit {
  version: Observable<string>;

  constructor(private coreService: CoreService) { }

  ngOnInit(): void {
    this.version = this.coreService.getVersion();
  }

}
