package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(Instant.now(), Instant.now()); // Handle case of no tweets
        }
        
        Instant start = tweets.get(0).getTimestamp();
        Instant end = start;

        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(start)) {
                start = tweet.getTimestamp();
            }
            if (tweet.getTimestamp().isAfter(end)) {
                end = tweet.getTimestamp();
            }
        }
        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            // Regex to find mentions: @ followed by valid username characters only
            String regex = "(?<=\\s|^|\\W)@([A-Za-z0-9_]+)(?=\\s|$|\\W)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                String username = matcher.group(1);
                // Check if the username is valid according to the specified criteria
                if (isValidUsername(username)) {
                    mentionedUsers.add(username.toLowerCase());
                }
            }
        }
        return mentionedUsers;
    }

    private static boolean isValidUsername(String username) {
        // Ensure the username is non-empty and matches the valid Twitter username pattern
        return username.matches("^[A-Za-z0-9_]+$") && !username.isEmpty();
    }
}
