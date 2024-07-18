export class Leave {
  employeeId: number;
  managerId: number;
  startDate: Date;
  endDate: Date;

  constructor(employeeId: number = 0, managerId: number = 0, startDate: Date = new Date(), endDate: Date = new Date()) {
    this.employeeId = employeeId;
    this.managerId = managerId;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
