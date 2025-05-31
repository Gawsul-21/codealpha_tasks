import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

// Main class to run the chatbot application
public class Chatbot {

    public static void main(String[] args) {
        ChatbotEngine engine = new ChatbotEngine();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm QueryBot. Type 'exit' to end the chat.");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("QueryBot: Goodbye! Have a nice day.");
                break;
            }
            String response = engine.getResponse(userInput);
            System.out.println("QueryBot: " + response);
        }

        scanner.close();
    }
}

// This class handles the chatbot logic and response generation
class ChatbotEngine {

    private Map<String, String[]> responses;
    private Random random;

    public ChatbotEngine() {
        random = new Random();
        responses = new HashMap<>();

        // Initialize responses for various keywords or topics
        responses.put("hello", new String[] {
            "Hi there!", "Hello!", "Hey! How can I help you today?"
        });
        responses.put("how are you", new String[] {
            "I'm just a bot, but I'm doing great!", "Doing well, thanks for asking!", "All systems operational!"
        });
        responses.put("weather", new String[] {
            "I don't have real-time weather info, sorry!", "Weather looks good from here!", "I hope it's sunny where you are."
        });
        responses.put("name", new String[] {
            "I'm QueryBot, your friendly chatbot.", "You can call me QueryBot."
        });
        responses.put("help", new String[] {
            "I'm here to chat with you. Ask me anything!", "Feel free to ask me questions."
        });
        responses.put("thank you", new String[] {
            "You're welcome!", "No problem!", "Glad I could help!"
        });
        responses.put("bye", new String[] {
            "Goodbye!", "See you later!", "Have a great day!"
        });
    }

    // Process user input and generate a response
    public String getResponse(String input) {
        String processedInput = preprocess(input);

        // Check for keywords in the input and return random matching response
        for (String keyword : responses.keySet()) {
            if (processedInput.contains(keyword)) {
                String[] possibleResponses = responses.get(keyword);
                return possibleResponses[random.nextInt(possibleResponses.length)];
            }
        }

        // Fallback response if no keyword matches
        return "Sorry, I didn't quite understand that. Can you try asking in a different way?";
    }

    // Convert input to lowercase and remove punctuation for simple matching
    private String preprocess(String input) {
        return input.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
    }
}
