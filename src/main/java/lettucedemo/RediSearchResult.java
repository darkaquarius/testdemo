package lettucedemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huishen
 * @date 2018/9/10 下午5:30
 */
public class RediSearchResult {
    public long totalResults;
    public final List<Document> docs;


    public RediSearchResult(List<Object> resp, boolean hasContent, boolean hasScores, boolean hasPayloads) {

        // Calculate the step distance to walk over the results.
        // The order of results is id, score (if withScore), payLoad (if hasPayloads), fields
        int step = 1;
        int scoreOffset = 0;
        int contentOffset = 1;
        int payloadOffset = 0;
        if (hasScores) {
            step += 1;
            scoreOffset = 1;
            contentOffset += 1;
        }
        if (hasContent) {
            step += 1;
            if (hasPayloads) {
                payloadOffset = scoreOffset + 1;
                step += 1;
                contentOffset += 1;
            }
        }

        // the first element is always the number of results
        totalResults = (Long) resp.get(0);
        docs = new ArrayList<>(resp.size() - 1);

        for (int i = 1; i < resp.size(); i += step) {

            String id = (String) resp.get(i);
            Double score = 1.0;
            byte[] payload = null;
            List fields = null;
            if (hasScores) {
                score = Double.valueOf(new String((byte[]) resp.get(i + scoreOffset)));
            }
            if (hasPayloads) {
                payload = (byte[]) resp.get(i + payloadOffset);
            }

            if (hasContent) {
                fields = (List) resp.get(i + contentOffset);
            }

            docs.add(Document.load(id, score, payload, fields));
        }
    }
}
