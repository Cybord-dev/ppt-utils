package com.unknown.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.unknown.error.PptUtilException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3Utils {

  private final AmazonS3 amazonS3;

  public S3Utils(String region) {
    this.amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
  }

  private void validateParams(String bucket, String path, String fileName) throws PptUtilException {
    if (Objects.isNull(bucket) || bucket.isBlank())
      throw new PptUtilException("bucket is mandatory");
    if (Objects.isNull(path) || path.isBlank()) throw new PptUtilException("path is mandatory");
    if (Objects.isNull(fileName) || fileName.isBlank())
      throw new PptUtilException("fileName is mandatory");
  }

  /**
   * Retrieves the file as inputstream if any error is present then {@link PptUtilException} is
   * produced
   *
   * @param bucket name of the bucket
   * @param path path in S3 bucket
   * @param fileName the name of the file
   * @return Returns file as inputstream
   * @throws {@link PptUtilException}
   */
  public InputStream getFileInputStream(String bucket, String path, String fileName)
      throws PptUtilException {
    try {
      validateParams(bucket, path, fileName);
      S3Object s3object = amazonS3.getObject(bucket.concat("/").concat(path), fileName);
      return s3object.getObjectContent();
    } catch (AmazonServiceException e) {
      log.error("AmazonServiceException:", e);
      throw new PptUtilException(e.getMessage());
    } catch (SdkClientException e) {
      log.error("SdkClientException:", e);
      throw new PptUtilException(e.getMessage());
    }
  }

  /**
   * Retrieves the file in String base 64 if exists in any other case would return a {@link
   * PptUtilException}
   *
   * @param bucket name of the bucket
   * @param path path in S3 bucket
   * @param fileName the name of the file
   * @return Returns file as base64 string
   * @throws {@link PptUtilException}
   */
  public String getFile(String bucket, String path, String fileName) throws PptUtilException {
    try {
      return new String(
          java.util.Base64.getEncoder()
              .encode(getFileInputStream(bucket, path, fileName).readAllBytes()));
    } catch (IOException e) {
      log.error("Error in file transformation.", e);
      throw new PptUtilException(e.getMessage());
    }
  }

  /**
   * Create S3 file, if the bucket or the path does not exist will thrown a {@link PptUtilException}
   *
   * @param bucket name of the bucket
   * @param path path in S3 bucket
   * @param file file in byte[] format
   * @throws {@link PptUtilException}
   */
  public void upsertFile(String bucket, String path, String fileName, byte[] file)
      throws PptUtilException {
    try {
      validateParams(bucket, path, fileName);
      InputStream inputStream = new ByteArrayInputStream(file);
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(inputStream.available());
      amazonS3.putObject(bucket.concat("/").concat(path), fileName, inputStream, metadata);
    } catch (AmazonServiceException e) {
      log.error("AmazonServiceException:", e);
      throw new PptUtilException(e.getMessage());
    } catch (SdkClientException e) {
      log.error("SdkClientException:", e);
      throw new PptUtilException(e.getMessage());
    } catch (IOException e) {
      log.error("IOException:", e);
      throw new PptUtilException(e.getMessage());
    }
  }

  /**
   * Deletes S3 file if the resource exists in the bucket in any other case would return a {@link
   * PptUtilException}
   *
   * @param bucket name of the bucket
   * @param path path in S3 bucket
   * @param fileName the name of the file
   * @throws {@link PptUtilException}
   */
  public void deleteFile(String bucket, String path, String fileName) throws PptUtilException {
    try {
      validateParams(bucket, path, fileName);
      amazonS3.deleteObject(bucket.concat("/").concat(path), fileName);
    } catch (AmazonServiceException e) {
      log.error("AmazonServiceException:", e);
      throw new PptUtilException(e.getMessage());
    } catch (SdkClientException e) {
      log.error("SdkClientException:", e);
      throw new PptUtilException(e.getMessage());
    }
  }
}
