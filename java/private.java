public void addBroucherDisclosures(String documentNames, List<String> pdfFileSeq, ResourceResolver resolver,
            MultipartEntityBuilder builder) {
        try {
            List<String> documents = null;
            if(documentNames!=null && !documentNames.equalsIgnoreCase("null")) {
                documents = Arrays.asList(documentNames.split(","));
                documents.forEach(documentName->{
                    String docPath = Disclosure_DAM_Path + documentName + ".pdf";
                    log.info("Searching dam for resource: " + documentName);
                    Resource resource = resolver.getResource(docPath);
                    if (resource != null) {
                        Asset asset = resource.adaptTo(Asset.class);
                        Resource original = asset.getOriginal();
                        String fileName = documentName + ".pdf";
                        log.info("Folder Language File === " + fileName);
                        try {
                            InputStream pdf = original.adaptTo(InputStream.class);
                            byte[] ba = IOUtils.toByteArray(pdf);
                            builder.addBinaryBody(fileName, ba, ContentType.APPLICATION_OCTET_STREAM, fileName);
                            pdfFileSeq.add(fileName);
                        } catch (IOException e) {
                            log.error("Exception: ", e);
                        }
                    }
                });
            }
        }catch (Exception e) {
              log.error    ("Exception: ", e);
        }
        
    }