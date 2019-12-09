#include "cdp4j-libuv.h"

static void alloc_buffer(uv_handle_t *handle, size_t suggested_size, uv_buf_t *buf) {
    buf->base = (char*) malloc(suggested_size);
    buf->len = (ULONG) suggested_size;
}

static void on_response(uv_stream_t* stream, ssize_t nread, const uv_buf_t* buf) {
  cdp4j_on_read_callback_java(stream->data, buf->base, (int) nread);
  free(buf->base);
}

static void on_async_write(uv_write_t* req, int status) {
  context_write* context = (context_write*) req->data;
  void* thread = context->pipe->data;
  cdp4j_on_write_callback_java(thread, req, status);
}

static void async_write(uv_async_t* handle) {
  context_write* context = (context_write*) handle->data;
  // uv_write_t *req = (uv_write_t*) context->write_request;
  // int ret = uv_write(req, (uv_stream_t*) context->pipe, context->buf, 1, on_async_write);
  void* thread = context->pipe->data;
  cdp4j_on_async_callback(thread, handle, on_async_write);
}

int cdp4j_spawn_process(uv_loop_t* loop, uv_process_t* handle, uv_process_options_t* options) {
  // options->exit_cb = cdp4j_on_process_exit;
  return uv_spawn(loop, handle, options);
}

int cdp4j_start_read(uv_pipe_t* out_pipe) {
  return uv_read_start((uv_stream_t*) out_pipe, alloc_buffer, on_response);
}

int cdp4j_write_pipe(uv_loop_t* loop, uv_async_t* handle, context_write* context) {
  if (uv_async_init(loop, handle, async_write) == CDP4J_UV_SUCCESS) {
    if (uv_async_send(handle) == CDP4J_UV_SUCCESS) {
      return CDP4J_UV_SUCCESS;
    }
  }
  return CDP4J_UV_SUCCESS - 1;
}
