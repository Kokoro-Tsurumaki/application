from fastapi import APIRouter
from ..dependencies import response_json_wrapper, make_response

router = APIRouter(prefix='', tags=["蒲公英"])

@router.get("/test")
@response_json_wrapper
async def read_users():
    return make_response(data="test")
