export default class Fetcher {
    baseUrl: string;
    
    constructor(baseUrl: string) {
        this.baseUrl = baseUrl;
    }

    get(url : string): Promise<any> {
        return fetch(this.concatBaseUrlWithUrl(url), { "method": "GET" });
    }

    concatBaseUrlWithUrl(url : string) {
        return `${this.baseUrl}${url}`;
    }
}