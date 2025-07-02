from fastapi import APIRouter


router = APIRouter(prefix='', tags=["蒲公英"])

@router.get("/test")
async def read_users():
    return {"message":"test"}
