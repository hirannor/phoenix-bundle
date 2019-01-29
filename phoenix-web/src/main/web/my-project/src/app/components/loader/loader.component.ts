import {Component} from '@angular/core';
import {LoaderService} from "../../services";

@Component({
  selector: 'phoenix-loader',
  templateUrl: './loader.component.html',
  styleUrls: [
    './loader.component.scss'
  ]
})
export class LoaderComponent  {

  constructor(private loaderService: LoaderService) {}

}
