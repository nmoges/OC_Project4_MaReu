# OC_Project4_MaReu

"Ma Réu" application

This repository contains all sources files of the "MaRéu" application. 

This version 1.0 contains the following functionalities :
- Display a list of Meetings with their information
  - Name Meeting
  - Room Meeting
  - Participants
  - Hour & date of the Meeting
  - Status of the Meeting
- Filter by date or by room the existing list of Meeting
- Add a new Meeting
- Delete an existing Meeting 

The current version of the app works for min API 21.

Several unit and instrumented tests are implemented, and testing few of the implemented functionalities. New tests
might be added later.

Unit tests (V1.0) :

FILE ListApiService.java 
- Tests of the ListApiService used to generated pre-defined fake list of Meetings
	- getListMeetingWithSuccess()
	- getListEmployeeWithSuccess()
	- addNewMeetingWithSuccess()
	- removeMeetingWithSuccess()
 
 FILE DateTimeFormatTest.java :
- Tests of the implemented methods displaying date & hour Meeting in the correct format 
	- testTimeConverter()
	- testDateConverter()
  
All tests passed. 

Instrumented tests (V1.0) :

FILE MeetingListTest.java 
- Tests of the ListMeetingsFragment :
	- checkIf_ListMeetingsFragment_isDisplayed ()
	- checkIf_listOfMeetings_isNotBeEmpty()
	- checkIf_deleteMeetingIcon_displays_confirmSuppressDialog()
	- checkIf_deleteMeetingIcon_displays_confirmSuppressDialog()
	- checkIf_AddMeetingFragment_isLaunched_onFabCLick()
 
All tests passed. 

FILE FilterListTest.java 
- Test of the filter functions in ListMeetingsFragment :
	- checkIf_clickOnItemsMenu_display_associatedDialog()
	- checkIf_filter_by_date_option1_isPerformedCorrectly()
	- checkIf_filter_by_date_option2_isPerformedCorrectly()
	- checkIf_filter_by_room_isPerformedCorrectly()

All tests passed. 

FILE AddMeetingTest.java
- Test of the differents views of AddMeetingTest, used to create a new Meeting
	- checkIf_all_Dialog_areCorrectlyDisplayed()
	- checkIf_RoomSelection_updateRoomTextInputEditText()
	- checkIf_DateSelection_updateDateTextInputEditText()
	- checkIf_HourSelection_updateHourTextInputEditText()
	- checkIf_clickOnParticipantsTextInputEditText_displayListEmployeesFragment()
	- check_ButtonsStatus_whenFragmentIsShown()
	- checkIf_ParticipantsSelection_updated_TextInputEditText_inAddMeetingFragment()
	- checkIf_MeetingCreation_isCorrectlyDone()
  
 All tests passed. 
