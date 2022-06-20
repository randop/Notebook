package scrapper

import (
    "fmt"

    "net/http"
    "net/url"

    "io"
    "bytes"
    "bufio"

    "regexp"

    "crypto/sha1"

    "appengine"
    "appengine/urlfetch"
    "appengine/memcache"
)

func init() {
    http.HandleFunc("/", indexHandler)
    http.HandleFunc("/image", imageHandler)
}

func indexHandler(w http.ResponseWriter, r *http.Request){
    fmt.Fprint(w, "version 1.0")
}

func imageHandler(w http.ResponseWriter, r *http.Request) {
    urlValue := r.FormValue("url")

    urlStruct, err := url.Parse(urlValue)

    if err != nil {
        http.Error(w, err.Error(), http.StatusInternalServerError)
        return
    }

    urlShaKey := sha1.Sum( []byte(urlValue) )
    hostShaKey := sha1.Sum( []byte(urlStruct.Host) )
    itemKey := fmt.Sprintf("%x%x", hostShaKey, urlShaKey )

    c := appengine.NewContext(r)

    // Get the item from the memcache
    if item, err := memcache.Get(c, itemKey); err == memcache.ErrCacheMiss {
        //c.Infof("item not in the cache")
    } else if err != nil {
        //c.Errorf("error getting item: %v", err)
    } else {
        w.Header().Set("content-type", "application/json")
        fmt.Fprintf(w, "%v", string(item.Value) )
        return
    }

    client := urlfetch.Client(c)
    resp, err := client.Get( urlValue )
    if err != nil {
        http.Error(w, err.Error(), http.StatusInternalServerError)
        return
    }
    defer resp.Body.Close()
    in := bufio.NewReader(resp.Body)
    ogImage := []byte("og:image")
    var response bytes.Buffer
    response.WriteString("{\"image\":\"")
    for {
        line, err := in.ReadBytes('>')
        if ( err!=nil ){
            break
        } else if ( err==io.EOF ) {
            break
        } else {
            if ( bytes.Contains(line, ogImage) ){
                re, errReg := regexp.Compile(`(?i)(?s)<meta.*property=(?:'og:image|"og:image"|og:image).*content=('[^']*'|"[^"]*"|[^'"][^\s>]*)(?:[^'">=]*|='[^']*'|="[^"]*"|=[^'"][^\s>]*)*>`)
                if ( errReg!=nil ){
                    break
                }
                s := string(line)
                matches := re.FindStringSubmatch(s)
                if ( len(matches)<=1 ){
                    break
                }
                response.WriteString(matches[1][1:len(matches[1])-1])
                break
            }
        }
    }

    response.WriteString("\"}")

    if ( response.Len()>9 ){
        item1 := &memcache.Item{
            Key:   itemKey,
            Value: []byte( response.String() ),
        }
        if err := memcache.Set(c, item1); err != nil {
            //void
        }
    }

    w.Header().Set("content-type", "application/json")
    fmt.Fprint(w, response.String() )
}