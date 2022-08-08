package emiel.model;

import java.time.LocalDate;

/**
 * @author Emiel Bloem
 * <p>
 * Class that saves the mood data
 */
public class DailyMood {
    private final LocalDate date;
    private final String mood, extraInfo;

    public DailyMood(LocalDate date, String mood, String extraInfo) {
        this.date = date;
        this.mood = mood;
        this.extraInfo = extraInfo;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.date, this.mood);
    }
}
