package utilsPagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UtilPag {
    //TODO: he puesto que todo esto es static, comprobar is funciona si no quito las proiedades de que sean static y
    //y pongo el metodo getResponseEntity en cada uno de los service
    @Autowired
    static
    RestTemplate restTemplate;

    @Value("${gitlab-miner.token}")
    private static String token;
    public static  <T1> ResponseEntity<T1[]> getResponseEntity(String uri, Class<T1[]> clase) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);

        HttpEntity<T1[]> request = new HttpEntity<>(null,headers);

        return restTemplate.exchange(uri,
                HttpMethod.GET,
                request,
                clase);
    }

    public static String getNextPageUrl(HttpHeaders headers) {
        String result = null;

        // If there is no link header, return null
        List<String> linkHeader = headers.get("Link");
        if (linkHeader == null)
            return null;

        // If the header contains no links, return null
        String links = linkHeader.get(0);
        if (links == null || links.isEmpty())
            return null;

        // Return the next page URL or null if none.
        for (String token : links.split(", ")) {
            if (token.endsWith("rel=\"next\"")) {
                // Found the next page. This should look something like
                // <https://api.github.com/repos?page=3&per_page=100>; rel="next"
                int idx = token.indexOf('>');
                result = token.substring(1, idx);
                break;
            }
        }

        return result;
    }
}
