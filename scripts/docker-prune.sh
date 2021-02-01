#!/usr/bin/env bash

DANGLING_IMAGES=$(docker images -f "dangling=true" -q)
if [[ -n "$DANGLING_IMAGES" ]]; then (docker rmi ${DANGLING_IMAGES}); fi
