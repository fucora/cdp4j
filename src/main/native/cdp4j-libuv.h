#include <stdlib.h>
#include <string.h>
#include "uv.h"

#define CDP4J_UV_SUCCESS 0

#define ULONG unsigned long

typedef union uv_data_s {
  uv_stream_t* stream;
  int fd;
} uv_data_t;

typedef struct context_write {
  uv_pipe_t* pipe;
  uv_buf_t* buf;
  uv_write_t *write_request;
} context_write;

int cdp4j_spawn_process(uv_loop_t*             loop,
                        uv_process_t*         handle,
                        uv_process_options_t* options);

int cdp4j_write_pipe(uv_loop_t*     loop,
                     uv_async_t*    handle,
                     context_write* context);

int cdp4j_start_read(uv_pipe_t* out_pipe);

void cdp4j_on_read_callback_java(void* thread, char* data, int let);

void cdp4j_on_write_callback_java(void* thread, uv_write_t* request, int status);

void cdp4j_on_async_callback(void* thread, uv_async_t* handle, void* callback);
