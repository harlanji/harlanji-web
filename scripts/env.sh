#!/bin/bash

# use with source

VERSION=48
IMAGE=harlanji-build
NAME=harlanji-build
DEV_PORT=34000
STATIC_PORT=34080
API_PORT=34081


DOMAIN=harlanji.com
HOSTS=#$DOMAIN


NAME_STATIC=harlanji-static
IMAGE_STATIC_LOCAL=$NAME_STATIC

IMAGE_STATIC=docker-registry.local:5000/harlanji.com/$NAME_STATIC:$VERSION


