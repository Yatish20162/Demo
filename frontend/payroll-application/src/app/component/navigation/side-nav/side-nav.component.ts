import { Component } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';

@Component({
  selector: 'side-nav',
  templateUrl: 'side-nav.component.html',
  styleUrl: 'side-nav.component.css',
  standalone: true,
  imports: [MatSidenavModule],
})
export class SideNavComponent {

}
