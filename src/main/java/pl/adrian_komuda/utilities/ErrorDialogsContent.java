package pl.adrian_komuda.utilities;

public enum ErrorDialogsContent {
    GENERAL(
            "Error has occurred!",
            "Try to exit the application, run it again and repeat Your steps. " +
                    "If the error will still occurring, please, contact the creator. Send him error code below."
    );

    private final String header;
    private final String content;

    ErrorDialogsContent(String header, String content) {
        this.header = header;
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }
}
