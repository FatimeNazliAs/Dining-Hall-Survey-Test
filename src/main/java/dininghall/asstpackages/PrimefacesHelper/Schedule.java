package dininghall.asstpackages.PrimefacesHelper;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleDisplayMode;

import java.time.LocalDateTime;

public class Schedule
{


    public DefaultScheduleEvent addEvent(LocalDateTime referenceDate)
    {
        return DefaultScheduleEvent.builder()
                .title("Champions League Match")
                .startDate(previousDay8Pm(referenceDate))
                .endDate(previousDay11Pm(referenceDate))
                .description("Team A vs. Team B")
                .url("https://www.uefa.com/uefachampionsleague/")
                .borderColor("orange")
                .build();
    }

    public DefaultScheduleEvent addEvent1(LocalDateTime referenceDate)
    {
        return DefaultScheduleEvent.builder()
                .title("Breakfast at Tiffanys (always resizable)")
                .startDate(nextDay9Am(referenceDate))
                .endDate(nextDay11Am(referenceDate))
                .description("all you can eat")
                .overlapAllowed(true)
                .resizable(true)
                .borderColor("#27AE60")
                .build();
    }

    public DefaultScheduleEvent addEvent2(LocalDateTime referenceDate)
    {
        return DefaultScheduleEvent.builder()
                .title("Plant the new garden stuff (always draggable)")
                .startDate(referenceDate)
                .endDate(referenceDate)
                .description("Trees, flowers, ...")
                .draggable(true)
                .borderColor("#27AE60")
                .build();
    }

    public DefaultScheduleEvent addEvent3(LocalDateTime referenceDate)
    {
        return DefaultScheduleEvent.builder()
                .title("Birthday Party")
                .startDate(today1Pm(referenceDate))
                .endDate(today6Pm(referenceDate))
                .description("Aragon")
                .overlapAllowed(true)
                .borderColor("#CB4335")
                .build();
    }

    public DefaultScheduleEvent addEvent4(LocalDateTime referenceDate,String description,boolean draggable,boolean overlapAllowed, boolean editTable, boolean resizable, ScheduleDisplayMode displayMode, String backgroundColor,String url)
    {
        return DefaultScheduleEvent.builder()
                .startDate(referenceDate)
                .endDate(referenceDate)
                .overlapAllowed(overlapAllowed)
                .draggable(draggable)
                .editable(editTable)
                .description(description)
                .resizable(resizable)
                .display(displayMode)
                .url(url)
              //  .display(ScheduleDisplayMode.BACKGROUND)
                .backgroundColor(backgroundColor)
             //   .backgroundColor("lightgreen")
                .build();

    }


    /**
     * bean methods
     */
    private LocalDateTime previousDay8Pm(LocalDateTime referenceDate)
    {
        return referenceDate.minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime previousDay11Pm(LocalDateTime referenceDate)
    {
        return referenceDate.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today1Pm(LocalDateTime referenceDate)
    {
        return referenceDate.withHour(13).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime theDayAfter3Pm(LocalDateTime referenceDate)
    {
        return referenceDate.plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today6Pm(LocalDateTime referenceDate)
    {
        return referenceDate.withHour(18).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay9Am(LocalDateTime referenceDate)
    {
        return referenceDate.plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay11Am(LocalDateTime referenceDate)
    {
        return referenceDate.plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime fourDaysLater3pm(LocalDateTime referenceDate)
    {
        return referenceDate.plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime sevenDaysLater0am(LocalDateTime referenceDate)
    {
        return referenceDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime eightDaysLater0am(LocalDateTime referenceDate)
    {
        return referenceDate.plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}
