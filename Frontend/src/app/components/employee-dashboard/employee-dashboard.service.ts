import { Injectable } from '@angular/core';
import { HttpClient , HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LeaveRequest } from '../../model/Leave-Request.model';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class EmployeeDashboardService {
  private apiUrl = 'http://localhost:8000/leave'; // Replace with your API base URL

  constructor(private http: HttpClient) {}

//  getRemainingLeave(employeeId:number):Observable<any>{
//   return this.http.et<any>('${this.apiUrl}/')
//  }

 getLeave(employeeId: number):Observable<LeaveRequest>{
  return this.http.get<LeaveRequest>(`${this.apiUrl}/viewLeaves?employeeId=${employeeId}`).pipe(
    catchError((error) => {
      console.error('Error fetching leave request count:', error);
      return throwError(error);
    })
    );
 }


 
}
