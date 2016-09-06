package com.devoteam.tracker.model;

public class DashboardProject {
	
	private String project; 		//1
	private String currentDay;		//2
	private int scheduledWeek2; 	//3
	private int attemptedWeek2; 	//4
	private int completedWeek2; 	//5
	private int partialWeek2; 		//6
	private int abortCustWeek2; 	//7
	private int abortDvtWeek2; 		//8
	private int scheduledWeek1; 	//9
	private int attemptedWeek1; 	//10
	private int completedWeek1; 	//11
	private int partialWeek1; 		//12
	private int abortCustWeek1; 	//13
	private int abortDvtWeek1; 		//14
	private int scheduledMon; 		//15
	private int attemptedMon; 		//16
	private int completedMon; 		//17
	private int partialMon; 		//18
	private int abortCustMon; 		//19
	private int abortDvtMon; 		//20
	private int scheduledTue; 		//21
	private int attemptedTue; 		//22
	private int completedTue; 		//23
	private int partialTue; 		//24
	private int abortCustTue; 		//25
	private int abortDvtTue; 		//26
	private int scheduledWed; 		//27
	private int attemptedWed; 		//28
	private int completedWed; 		//29
	private int partialWed; 		//30
	private int abortCustWed; 		//31
	private int abortDvtWed; 		//32
	private int scheduledThu; 		//33
	private int attemptedThu; 		//34
	private int completedThu; 		//35
	private int partialThu; 		//36
	private int abortCustThu; 		//37
	private int abortDvtThu; 		//38
	private int scheduledFri; 		//39
	private int attemptedFri; 		//40
	private int completedFri; 		//41
	private int partialFri; 		//42
	private int abortCustFri; 		//43
	private int abortDvtFri; 		//44
	private int scheduledSat; 		//45
	private int attemptedSat; 		//46
	private int completedSat; 		//47
	private int partialSat; 		//48
	private int abortCustSat; 		//49
	private int abortDvtSat; 		//50
	private int scheduledSun; 		//51
	private int attemptedSun; 		//52
	private int completedSun; 		//53
	private int partialSun; 		//54
	private int abortCustSun; 		//55
	private int abortDvtSun; 		//56
	private int week1Scheduled;		//57
	private int week2Scheduled;		//58
	private int week3Scheduled;		//59
	private int week4Scheduled;		//60
	private int week5Scheduled;		//61
	private int week6Scheduled;		//62
	
	public DashboardProject(
			String project,
			String currentDay,
			int scheduledWeek2,
			int attemptedWeek2,
			int completedWeek2,
			int partialWeek2,
			int abortCustWeek2,
			int abortDvtWeek2,
			int scheduledWeek1,
			int attemptedWeek1,
			int completedWeek1,
			int partialWeek1,
			int abortCustWeek1,
			int abortDvtWeek1,
			int scheduledMon,
			int attemptedMon,
			int completedMon,
			int partialMon,
			int abortCustMon,
			int abortDvtMon,
			int scheduledTue,
			int attemptedTue,
			int completedTue,
			int partialTue,
			int abortCustTue,
			int abortDvtTue,
			int scheduledWed,
			int attemptedWed,
			int completedWed,
			int partialWed,
			int abortCustWed,
			int abortDvtWed,
			int scheduledThu,
			int attemptedThu,
			int completedThu,
			int partialThu,
			int abortCustThu,
			int abortDvtThu,
			int scheduledFri,
			int attemptedFri,
			int completedFri,
			int partialFri,
			int abortCustFri,
			int abortDvtFri,
			int scheduledSat,
			int attemptedSat,
			int completedSat,
			int partialSat,
			int abortCustSat,
			int abortDvtSat,
			int scheduledSun,
			int attemptedSun,
			int completedSun,
			int partialSun,
			int abortCustSun,
			int abortDvtSun,
			int week1Scheduled,
			int week2Scheduled,
			int week3Scheduled,
			int week4Scheduled,
			int week5Scheduled,
			int week6Scheduled) {
		this.project = project;
		this.currentDay = currentDay;
		this.scheduledWeek2 = scheduledWeek2;
		this.attemptedWeek2 = attemptedWeek2;
		this.completedWeek2 = completedWeek2; 
		this.partialWeek2 = partialWeek2; 
		this.abortCustWeek2 = abortCustWeek2;
		this.abortDvtWeek2 = abortDvtWeek2;
		this.scheduledWeek1 = scheduledWeek1;
		this.attemptedWeek1 = attemptedWeek1;
		this.completedWeek1 = completedWeek1; 
		this.partialWeek1 = partialWeek1; 
		this.abortCustWeek1 = abortCustWeek1;
		this.abortDvtWeek1 = abortDvtWeek1;
		this.scheduledMon = scheduledMon;
		this.attemptedMon = attemptedMon;
		this.completedMon = completedMon; 
		this.partialMon = partialMon; 
		this.abortCustMon = abortCustMon;
		this.abortDvtMon = abortDvtMon;
		this.scheduledTue = scheduledTue;
		this.attemptedTue = attemptedTue;
		this.completedTue = completedTue; 
		this.partialTue = partialTue; 
		this.abortCustTue = abortCustTue;
		this.abortDvtTue = abortDvtTue;
		this.scheduledWed = scheduledWed;
		this.attemptedWed = attemptedWed;
		this.completedWed = completedWed; 
		this.partialWed = partialWed; 
		this.abortCustWed = abortCustWed;
		this.abortDvtWed = abortDvtWed;
		this.scheduledThu = scheduledThu;
		this.attemptedThu = attemptedThu;
		this.completedThu = completedThu; 
		this.partialThu = partialThu; 
		this.abortCustThu = abortCustThu;
		this.abortDvtThu = abortDvtThu;
		this.scheduledFri = scheduledFri;
		this.attemptedFri = attemptedFri;
		this.completedFri = completedFri; 
		this.partialFri = partialFri; 
		this.abortCustFri = abortCustFri;
		this.abortDvtFri = abortDvtFri;
		this.scheduledSat = scheduledSat;
		this.attemptedSat = attemptedSat;
		this.completedSat = completedSat; 
		this.partialSat = partialSat; 
		this.abortCustSat = abortCustSat;
		this.abortDvtSat = abortDvtSat;
		this.scheduledSun = scheduledSun;
		this.attemptedSun = attemptedSun;
		this.completedSun = completedSun; 
		this.partialSun = partialSun; 
		this.abortCustSun = abortCustSun;
		this.abortDvtSun = abortDvtSun;
		this.week1Scheduled = week1Scheduled;
		this.week2Scheduled = week2Scheduled;
		this.week3Scheduled = week3Scheduled;
		this.week4Scheduled = week4Scheduled;
		this.week5Scheduled = week5Scheduled;
		this.week6Scheduled = week6Scheduled;
	}
	
	public String getProject() {
		return project;
	}
	
	public String getCurrentDay() {
		return currentDay;
	}

	public String getScheduledWeek2() {
		return String.valueOf(scheduledWeek2);
	}
	
	public String getAttemptedWeek2() {
		return String.valueOf(attemptedWeek2);
	}
	
	public String getCompletedWeek2() {
		return String.valueOf(completedWeek2);
	}
	
	public String getPartialWeek2() {
		return String.valueOf(partialWeek2);
	}
	
	public String getAbortCustWeek2() {
		return String.valueOf(abortCustWeek2);
	}
	
	public String getAbortDvtWeek2() {
		return String.valueOf(abortDvtWeek2);
	}

	public String getScheduledWeek1() {
		return String.valueOf(scheduledWeek1);
	}
	
	public String getAttemptedWeek1() {
		return String.valueOf(attemptedWeek1);
	}
	
	public String getCompletedWeek1() {
		return String.valueOf(completedWeek1);
	}
	
	public String getPartialWeek1() {
		return String.valueOf(partialWeek1);
	}
	
	public String getAbortCustWeek1() {
		return String.valueOf(abortCustWeek1);
	}
	
	public String getAbortDvtWeek1() {
		return String.valueOf(abortDvtWeek1);
	}

	public String getScheduledMon() {
		return String.valueOf(scheduledMon);
	}
	
	public String getAttemptedMon() {
		return String.valueOf(attemptedMon);
	}
	
	public String getCompletedMon() {
		return String.valueOf(completedMon);
	}
	
	public String getPartialMon() {
		return String.valueOf(partialMon);
	}
	
	public String getAbortCustMon() {
		return String.valueOf(abortCustMon);
	}
	
	public String getAbortDvtMon() {
		return String.valueOf(abortDvtMon);
	}

	public String getScheduledTue() {
		return String.valueOf(scheduledTue);
	}
	
	public String getAttemptedTue() {
		return currentDay.equals("Monday")?"":String.valueOf(attemptedTue);
	}
	
	public String getCompletedTue() {
		return currentDay.equals("Monday")?"":String.valueOf(completedTue);
	}
	
	public String getPartialTue() {
		return currentDay.equals("Monday")?"":String.valueOf(partialTue);
	}
	
	public String getAbortCustTue() {
		return currentDay.equals("Monday")?"":String.valueOf(abortCustTue);
	}
	
	public String getAbortDvtTue() {
		return currentDay.equals("Monday")?"":String.valueOf(abortDvtTue);
	}

	public String getScheduledWed() {
		return String.valueOf(scheduledWed);
	}
	
	public String getAttemptedWed() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")
				?"":String.valueOf(attemptedWed);
	}
	
	public String getCompletedWed() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")
				?"":String.valueOf(completedWed);
	}
	
	public String getPartialWed() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")
				?"":String.valueOf(partialWed);
	}
	
	public String getAbortCustWed() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")
				?"":String.valueOf(abortCustWed);
	}
	
	public String getAbortDvtWed() {
		return	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")
				?"":String.valueOf(abortDvtWed);
	}

	public String getScheduledThu() {
		return String.valueOf(scheduledThu);
	}
	
	public String getAttemptedThu() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")
				?"":String.valueOf(attemptedThu);
	}
	
	public String getCompletedThu() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")
				?"":String.valueOf(completedThu);
	}
	
	public String getPartialThu() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")
				?"":String.valueOf(partialThu);
	}
	
	public String getAbortCustThu() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")
				?"":String.valueOf(abortCustThu);
	}
	
	public String getAbortDvtThu() {
		return	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")
				?"":String.valueOf(abortDvtThu);
	}

	public String getScheduledFri() {
		return String.valueOf(scheduledFri);
	}
	
	public String getAttemptedFri() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")
				?"":String.valueOf(attemptedFri);
	}
	
	public String getCompletedFri() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")
				?"":String.valueOf(completedFri);
	}
	
	public String getPartialFri() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")
				?"":String.valueOf(partialFri);
	}
	
	public String getAbortCustFri() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")
				?"":String.valueOf(abortCustFri);
	}
	
	public String getAbortDvtFri() {
		return	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")
				?"":String.valueOf(abortDvtFri);
	}

	public String getScheduledSat() {
		return String.valueOf(scheduledSat);
	}
	
	public String getAttemptedSat() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")
				?"":String.valueOf(attemptedSat);
	}
	
	public String getCompletedSat() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")
				?"":String.valueOf(completedSat);
	}
	
	public String getPartialSat() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")
				?"":String.valueOf(partialSat);
	}
	
	public String getAbortCustSat() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")
				?"":String.valueOf(abortCustSat);
	}
	
	public String getAbortDvtSat() {
		return	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")
				?"":String.valueOf(abortDvtSat);
	}

	public String getScheduledSun() {
		return String.valueOf(scheduledSun);
	}
	
	public String getAttemptedSun() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")||
				currentDay.equals("Saturday")
				?"":String.valueOf(attemptedSun);
	}
	
	public String getCompletedSun() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")||
				currentDay.equals("Saturday")
				?"":String.valueOf(completedSun);
	}
	
	public String getPartialSun() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")||
				currentDay.equals("Saturday")
				?"":String.valueOf(partialSun);
	}
	
	public String getAbortCustSun() {
		return 	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")||
				currentDay.equals("Saturday")
				?"":String.valueOf(abortCustSun);
	}
	
	public String getAbortDvtSun() {
		return	currentDay.equals("Monday")||
				currentDay.equals("Tuesday")||
				currentDay.equals("Wednesday")||
				currentDay.equals("Thursday")||
				currentDay.equals("Friday")||
				currentDay.equals("Saturday")
				?"":String.valueOf(abortDvtSun);
	}

	public String getWeek1Scheduled() {
		return String.valueOf(week1Scheduled);
	}

	public String getWeek2Scheduled() {
		return String.valueOf(week2Scheduled);
	}

	public String getWeek3Scheduled() {
		return String.valueOf(week3Scheduled);
	}

	public String getWeek4Scheduled() {
		return String.valueOf(week4Scheduled);
	}

	public String getWeek5Scheduled() {
		return String.valueOf(week5Scheduled);
	}

	public String getWeek6Scheduled() {
		return String.valueOf(week6Scheduled);
	}
	
}