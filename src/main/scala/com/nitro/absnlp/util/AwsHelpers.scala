package com.nitro.absnlp.util

object AwsHelpers {

  import java.net.URLEncoder

  type S3Path = String

  /**
   * Evaluates to a raw S3 url pointing to the given path in the given bucket.
   * Uses the access and secret keys for validation. Secret key is URL-encoded.
   */
  def s3rawPath(accessKey: String, secretKey: String)(bucket: String)(path: String): S3Path =
    s3path("s3raw")(accessKey, secretKey)(bucket)(path)

  /**
   * Evaluates to a raw S3 url pointing to the given path in the given bucket.
   * Uses the access and secret keys for validation. Secret key is URL-encoded.
   */
  def s3nPath(accessKey: String, secretKey: String)(bucket: String)(path: String): S3Path =
    s3path("s3n")(accessKey, secretKey)(bucket)(path)

  /** Construts a valid S3 URL for addressing a particular path in a bucket, using the given keys. */
  @inline private def s3path(pathType: String)(accessKey: String, secretKey: String)(bucket: String)(path: String): S3Path =
    s"""$pathType://$accessKey:${URLEncoder.encode(secretKey, "UTF-8")}@$bucket/$path"""

}

