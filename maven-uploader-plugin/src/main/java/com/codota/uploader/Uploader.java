/*
 * Copyright 2015 Codota
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codota.uploader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class Uploader {

    private final String endpoint;
    private final CloseableHttpClient httpClient;
    private final String token;

    public Uploader(String codotaEndpoint, String token) {
        this.endpoint = codotaEndpoint;
        this.httpClient = HttpClientBuilder.create().build();
        this.token = token;
    }


    public void uploadFile(File file) throws IOException {
        uploadFile(file, endpoint);
    }

    private void uploadFile(File file, String uploadUrl) throws IOException {
            HttpPut putRequest = new HttpPut(uploadUrl);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("code", new FileBody(file));
            final HttpEntity entity = builder.build();
            putRequest.setEntity(entity);

            putRequest.setHeader("enctype", "multipart/form-data");
            putRequest.setHeader("authorization", "bearer " + token);
        System.out.println("TOKEN=" + token);
        System.out.println("URL=" + uploadUrl);
            httpClient.execute(putRequest, new UploadResponseHandler());
    }


    private class UploadResponseHandler implements ResponseHandler<Object> {
        @Override
        public Object handleResponse(HttpResponse response)
                throws IOException {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            final int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("CODE: " + statusCode);
            System.out.println("LINE:" + responseString);
            if (statusCode == HttpStatus.SC_OK) {
                System.out.println("Success! " + responseString);
            } else {
                System.out.println("Request failed with status " + responseString + response.toString());
            }
            return null;
        }

    }


}
